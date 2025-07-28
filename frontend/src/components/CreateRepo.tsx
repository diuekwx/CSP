import { useState, type FormEvent } from "react";
import { useNavigate } from 'react-router-dom';
import Navbar from "./NavBar";

interface FormData {
    title: string;
    description: string;
    isPublic: boolean;
}

export default function CreateRepo() {
    const token = localStorage.getItem("jwt");
    const [formData, setFormData] = useState<FormData>({
        title: '',
        description: '',
        isPublic: true
    });


    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.checked });
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/repository/create', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error("Failed to create repo");
               
            }

            const data = await response.json();
            const title = data.title;
            const user = data.userId.username;
            navigate(`/${user}/${title}`);
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <>
        <Navbar/>
        <div className="max-w-xl mx-auto mt-10 bg-white dark:bg-gray-900 border border-gray-200 dark:border-gray-700 rounded-lg shadow p-6">
            <form onSubmit={handleSubmit} className="space-y-6">
                <div>
                    <label htmlFor="title" className="block text-sm font-medium text-gray-900 dark:text-white mb-1">
                        Repository name
                    </label>
                    <input
                        type="text"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        className="w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 px-3 py-2 text-sm shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500 text-gray-900 dark:text-white"
                        placeholder="awesome-repo"
                        required
                    />
                </div>

                <div>
                    <label htmlFor="description" className="block text-sm font-medium text-gray-900 dark:text-white mb-1">
                        Description (optional)
                    </label>
                    <input
                        type="text"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        className="w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 px-3 py-2 text-sm shadow-sm focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500 text-gray-900 dark:text-white"
                        placeholder="Describe your project"
                    />
                </div>

                <div className="flex items-center">
                    <input
                        type="checkbox"
                        name="isPublic"
                        id="isPublic"
                        checked={formData.isPublic}
                        onChange={handleCheckboxChange}
                        className="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500 dark:bg-gray-800 dark:border-gray-600"
                    />
                    <label htmlFor="isPublic" className="ml-2 block text-sm text-gray-900 dark:text-white">
                        Make repository public
                    </label>
                </div>

                <button
                    type="submit"
                    className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-semibold rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                >
                    Create Repository
                </button>
            </form>
        </div>
        </>
    );
}
