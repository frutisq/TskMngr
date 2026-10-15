export function getUserRole() {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        const authorities = payload.authorities ?? [];
        return authorities.includes("ROLE_ADMIN") ? "ADMIN" : "USER";
    } catch {
        return null;
    }
}
