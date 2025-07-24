import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getCurrentUser } from "../api/auth";


interface Project {
  id: number;
  title: string;
  isPublic: boolean;
  updatedAt: Date;
}


export default function Projects() {
    const [projects, setProjects] = useState<Project[]>([]);
    const [user, setUser] = useState<{ id: string; username: string } | null>(null);


    const token = localStorage.getItem("jwt");


    useEffect(() => {
        const fetchUser = async () => {
            try{
                const data = await getCurrentUser();
                setUser(data);
            }
            catch (err){
                console.error("error fetching user:", err);
            }
        }

        const fetchData = async () => {
        try {
            const response = await fetch("http://localhost:8080/repository/projects", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            });

            if (!response.ok) {
            throw new Error("failed to fetch projects");
            }

            const data: Project[] = await response.json();
            setProjects(data);
        } catch (error) {
            console.error("Error fetching projects:", error);
        }
        };

        fetchData();
        fetchUser();
    }, []);
    
    if (!user) {
        return <p className="p-4">Loading...</p>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold mb-4">Your Projects</h1>
            {projects.length === 0 ? (
                <p>No projects found.</p>
            ) : (
                <div className="grid gap-4">
                {projects.map((project) => (
                    <Link
                    to={`/${encodeURIComponent(user.username)}/${encodeURIComponent(project.title)}`}
                    key={project.id}
                    className="border p-4 rounded shadow hover:bg-gray-100 transition"
                    >
                    <h2 className="text-xl font-semibold">{project.title}</h2>
                    <p className="text-gray-600">
                        {project.isPublic ? "Public" : "Private"}
                    </p>
                    </Link>
                ))}
                </div>
            )}
        </div>

    );
}
