import { API_BASE_URL } from "./url";

export async function getCurrentUser() {
  const res = await fetch(`${API_BASE_URL}/api/auth/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  });

  if (!res.ok) throw new Error("Failed to fetch user");

  return await res.json();
}
