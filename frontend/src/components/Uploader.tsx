import { useState } from "react";

interface Props {
  repoName?: string;
  onUploadSuccess: () => void;

}

export default function ContributionUploader({repoName, onUploadSuccess}: Props) {
  const [file, setFile] = useState<File | null>(null);

  const [description, setDescription] = useState("");
  const [status, setStatus] = useState<"idle" | "uploading" | "success" | "error">("idle");
  const [errorMessage, setErrorMessage] = useState("");

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!file || !repoName || !description) {
      alert("Please fill out all fields and choose a file.");
      return;
    }

    setStatus("uploading");
    setErrorMessage("");

    const formData = new FormData();
    formData.append("file", file);
    formData.append("repoName", repoName);
    formData.append("description", description);

    try {
      const res = await fetch("http://localhost:8080/contribution/contribution", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwt") || ""}`,
        },
        body: formData,
      });

      if (!res.ok) {
        const error = await res.json();
        throw new Error(error.error || "Upload failed");
      }

      const data = await res.json();
      
      console.log("Upload successful:", data);
      setStatus("success");
      onUploadSuccess();

    } catch (err: any) {
      console.error("Upload error:", err);
      setErrorMessage(err.message);
      setStatus("error");
    }
  };

  return (
    <div className="p-4 max-w-md mx-auto border rounded shadow">
      <h2 className="text-xl font-bold mb-4">Upload Contribution</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <textarea
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="w-full p-2 border rounded"
          rows={3}
          required
        />
        <input
          type="file"
          onChange={handleFileChange}
          className="w-full"
          required
        />
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
          disabled={status === "uploading"}
        >
          {status === "uploading" ? "Uploading..." : "Submit"}
        </button>
      </form>
      {status === "success" && (
        <p className="text-green-600 mt-3">Upload successful!</p>
      )}
      {status === "error" && (
        <p className="text-red-600 mt-3">Upload failed: {errorMessage}</p>
      )}
    </div>
  );
}
