import api from "./axios";

export function getAllUsers() {
    return api.get("/users");
}

export function getUser(id) {
    return api.get(`/users/${id}`);
}

export function createUser(user) {
    return api.post("/users", user);
}

export function updateUser(id, user) {
    return api.put(`/users/${id}`, user);
}

export function deleteUser(id) {
    return api.delete(`/users/${id}`);
}