import api from "./axios";

export function getComments(taskId) {
    return api.get(`/comments/task/${taskId}`);
}

export function addComment(taskId, userId, comment) {
    return api.post(
        `/comments/task/${taskId}/user/${userId}`,
        comment
    );
}

export function updateComment(id, comment) {
    return api.put(`/comments/${id}`, comment);
}

export function deleteComment(id) {
    return api.delete(`/comments/${id}`);
}