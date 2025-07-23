import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

interface RepoData {
  name: string;
  owner: string;
  description: string;
  isEmpty: boolean;
  visibility: "public" | "private"; // lol
  files: []
}

interface ArtworkVersion {
  id: string;
  fileUrl: string;
  versionDescription: string;
  uploadedAt: string;
}

export default function RepoPage() {
  const { owner, repoName } = useParams();
  const [repo, setRepo] = useState<RepoData | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    fetch(`http://localhost:8080/repository/${owner}/${repoName}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then(setRepo)
      .catch((err) => console.error("Failed to fetch repo:", err));
  }, [owner, repoName]);

  if (!repo) {
    return <div className="p-6 text-gray-600">Loading repository...</div>;
  }

  return (
    <div className="max-w-6xl mx-auto p-6">
      
      <div className="flex items-center justify-between border-b pb-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">
            <span className="text-gray-500">{repo.owner}/</span>
            <span>{repo.name}</span>
          </h1>
          <p className="text-gray-600 mt-1">{repo.description || "No description"}</p>
        </div>
        <span
          className={`px-2 py-1 text-xs font-semibold rounded ${
            repo.visibility === "public"
              ? "bg-green-100 text-green-700"
              : "bg-gray-200 text-gray-700"
          }`}
        >
          {repo.visibility}
        </span>
      </div>

      
      <div className="flex space-x-8 mt-6 border-b text-sm font-medium text-gray-600">
        <button className="pb-2 border-b-2 border-blue-600 text-blue-600">Code</button>
        <button className="pb-2 hover:text-gray-900">Issues</button>
        <button className="pb-2 hover:text-gray-900">Pull Requests</button>
        <button className="pb-2 hover:text-gray-900">Settings</button>
      </div>

      
      <div className="mt-6 border rounded-md p-4 bg-gray-50 text-gray-600">
        <p>📁 File explorer coming soon...</p>
      </div>
    </div>
  );
}
