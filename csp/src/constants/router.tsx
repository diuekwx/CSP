
import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../components/LandingPage";
import LoginPage from "../components/LoginPage";
import HomePage from "../components/HomePage";

export const router = createBrowserRouter([
    {path: "/", element: <LandingPage/>},
    {path: "/login", element: <LoginPage/>},
    {path: "/home", element: <HomePage/>}
])
