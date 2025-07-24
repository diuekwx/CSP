
import { createBrowserRouter } from "react-router-dom";
import LandingPage from "../components/LandingPage";
import LoginPage from "../components/LoginPage";
import HomePage from "../components/HomePage";
import CreateRepo from "../components/CreateRepo";
import SignupPage from "../components/SignupPage";
import RepoPage from "../components/RepoPage";
import Projects from "../components/Projects";

export const router = createBrowserRouter([
    {path: "/", element: <LandingPage/>},
    {path: "/login", element: <LoginPage/>},
    {path: "/home", element: <HomePage/>},
    {path: "/new", element: <CreateRepo/>},
    {path: "/signup", element: <SignupPage/>},
    {path: "/:owner/:repoName", element: <RepoPage/>},
    {path: "/projects", element: <Projects/>},

    
])
