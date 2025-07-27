import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const res = await fetch("http://localhost:8080/api/auth/signin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });
            if (!res.ok) {
                console.error("Login failed");
                return;
            }

            const data = await res.json();
            console.log("login success: ", data);
            localStorage.setItem("jwt", data.token)
            navigate('/home');
        } catch (error) {
            console.log("error during login");
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="w-full max-w-sm bg-white border border-gray-300 rounded-xl shadow-md p-6">
                <h2 className="text-2xl font-semibold text-gray-800 text-center mb-6">Sign in to CSPHUB</h2>
                <form onSubmit={handleLogin} className="space-y-4">
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">
                            Username
                        </label>
                        <input
                            type="text"
                            name="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Username"
                            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-500"
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                            Password
                        </label>
                        <input
                            type="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-500"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition-colors"
                    >
                        Sign in
                    </button>
                </form>
                <p className="text-xs text-gray-500 text-center mt-6">
                    No Account ? <a href="#" className="text-blue-500 hover:underline">Create an account</a>
                </p>
            </div>
        </div>
    );
}
