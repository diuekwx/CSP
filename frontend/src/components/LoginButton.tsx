import { useNavigate } from 'react-router-dom';

export default function LoginButton() {
    const navigate = useNavigate();

    return (
        <button
            onClick={() => navigate("/login")}
            className="relative inline-flex items-center px-6 py-3 text-lg font-semibold text-white bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 rounded-2xl shadow-lg hover:scale-105 hover:shadow-xl transition-transform duration-300 ease-in-out"
        >
            <span className="z-10">Login</span>
            <span className="absolute inset-0 bg-black opacity-0 hover:opacity-10 rounded-2xl transition-opacity duration-300" />
        </button>
    );
}
