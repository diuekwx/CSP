import {useState} from 'react';
import { Navigate, useNavigate } from 'react-router-dom';

export default function LoginPage() {
    
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const navigate = useNavigate();

    const handleSubmit = async () => {
        const res = await fetch('localhost:5050/api/auth/login');
        if (!res.ok){
            throw new Error(`error : ${res.status}`);
        }
        const result = await res.json();
        // should probably return username 
        console.log(result);
        navigate('/home')
    }



    return (
        <>
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder='Username'
                />
                <input
                    type="text"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder='Password'
                />
                <button type='submit'>
                    Log In
                </button>


            </form>

        </div>


        </>
    )
}