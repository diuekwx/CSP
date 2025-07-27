import { useState } from 'react';

export default function SignupPage() {
  const [formData, setFormData] = useState({
    email: '',
    username: '',
    password: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      const res = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!res.ok) {
        const errData = await res.json();
        throw new Error(errData.message || 'Signup failed');
      }

      setSuccess('Account created successfully!');
      setFormData({ email: '', username: '', password: '' });
    } catch (err: any) {
      setError(err.message);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md space-y-6"
      >
        <h2 className="text-2xl font-bold text-center">Sign Up</h2>

        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          className="w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
        />

        <input
          name="username"
          type="text"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
          required
          className="w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
        />

        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          className="w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
        />

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-xl hover:bg-blue-700 transition"
        >
          Sign Up
        </button>

        {error && <p className="text-red-600 text-sm text-center">{error}</p>}
        {success && <p className="text-green-600 text-sm text-center">{success}</p>}
      </form>
    </div>
  );
}
