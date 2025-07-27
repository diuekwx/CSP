import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ContributionUploader from "./Uploader";
import Navbar from "./NavBar";

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
  ogFileName: string;
}

export default function RepoPage() {
  const { owner, repoName } = useParams();
  const [repo, setRepo] = useState<RepoData | null>(null);
  const [version, setVersion] = useState<ArtworkVersion[] | null> ([]);
  const [showUploader, setShowUploader] = useState(false);
  const token = localStorage.getItem("jwt");

  useEffect(() => {
    const fetchRepo = async () => {
      
      if (!token) {
        console.error("No JWT token found in localStorage");
        return;
      }

      try {
        const res = await fetch(`http://localhost:8080/repository/${owner}/${repoName}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) {
          throw new Error(`Failed to fetch repo: ${res.status} ${res.statusText}`);
        }

        const data = await res.json();
        setRepo(data);
        console.log(data.files[0].ogFileName);
        setVersion(data.files);
      } catch (err) {
        console.error("Error fetching repo:", err);
      }
    };

    fetchRepo();
  }, [owner, repoName]);

  if (!repo) {
    return <div className="p-6 text-gray-600">Loading repository...</div>;
  }

  return (
    <>
    <Navbar/>
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
            // fix this lol
            repo.visibility === "public"
              ? "bg-green-700 text-green-700"
              : "bg-gray-900 text-gray-700"
          }`}
        >
          {repo.visibility}
        </span>
      </div>

      
      <div className="flex space-x-8 mt-6 border-b text-sm font-medium text-gray-600">
        <button className="pb-2 border-b-2 border-blue-600 text-blue-600">Project Files</button>
        <button className="pb-2 hover:text-gray-900">Settings</button>
      </div>

      <div className="mt-6 border rounded-md p-4 bg-gray-50 text-gray-600">
        
        <button
          onClick={() => setShowUploader((prev) => !prev)}
          className="px-4 py-2 bg-blue-600 text-white rounded">
        {showUploader ? "Cancel Upload" : "Upload Contribution"}
        </button>

        {showUploader && <ContributionUploader repoName={repoName} />}
        {version && version.length > 0 && (
          <div className="mt-4">
            <h2 className="text-lg font-semibold text-gray-700 mb-2">Uploaded Files</h2>
            <ul className="list-disc pl-5 space-y-1">
              {version.map((v) => (
                <li key={v.id}>{v.ogFileName}</li>
              ))}
            </ul>
          </div>
        )}
        
      </div>

    </div>
    </>
  );
}
