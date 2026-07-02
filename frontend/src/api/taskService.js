import api from "./axios";

export function getAllTasks() {
    return api.get("/tasks");
}

export function getTask(id) {
    return api.get(`/tasks/${id}`);
}

export function createTask(task) {
    return api.post("/tasks", task);
}

export function updateTask(id, task) {
    return api.put(`/tasks/${id}`, task);
}

export function deleteTask(id) {
    return api.delete(`/tasks/${id}`);
}

export function assignUser(taskId, userId) {
    return api.patch(`/tasks/${taskId}/assign/${userId}`);
}

export function createTaskForUser(userId, task) {
    return api.post(`/tasks/user/${userId}`, task);
}