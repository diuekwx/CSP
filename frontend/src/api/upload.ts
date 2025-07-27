async function uploadContribution({
  file,
  repoName,
  description,
}: {
  file: File;
  repoName: string;
  description: string;
}): Promise<{ filename: string; path: string }> {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("repoName", repoName);
  formData.append("description", description);

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

  return await res.json();
}