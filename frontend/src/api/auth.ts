export async function getCurrentUser() {
  const res = await fetch("http://localhost:8080/api/auth/me", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  });

  if (!res.ok) throw new Error("Failed to fetch user");

  return await res.json();
}
