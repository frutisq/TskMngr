<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import { getTask, updateTask, assignUser } from "../api/taskService";
import { getComments, addComment } from "../api/commentService";
import { getAllUsers } from "../api/userService";

const route = useRoute();
const router = useRouter();

const task = ref({});

const comments = ref([]);

const users = ref([]);

const selectedUser = ref("");

const newComment = ref("");

async function loadTask() {
    const response = await getTask(route.params.id);
    task.value = response.data;

    if (task.value.userId) {
        selectedUser.value = task.value.userId;
    }
}

async function loadUsers() {
    const response = await getAllUsers();
    users.value = response.data;
}

async function loadComments() {
    const response = await getComments(route.params.id);
    comments.value = response.data;
}

async function saveTask() {

    await updateTask(task.value.id, task.value);

    alert("Task updated");
}

async function assign() {

    if (!selectedUser.value) return;

    await assignUser(task.value.id, selectedUser.value);

    await loadTask();

    alert("User assigned");
}

async function addNewComment() {
    if (!newComment.value.trim())
        return;

    await addComment(
        task.value.id,
        {
            text: newComment.value
        }
    );

    newComment.value = "";
    await loadComments();
}

function back() {
    router.push("/");
}

onMounted(async () => {

    await loadTask();

    await loadUsers();

    await loadComments();

});
</script>

<template>

<div class="container">

<h1>Edit task</h1>

<label>Title</label>

<input
v-model="task.title"
/>

<label>Description</label>

<textarea
v-model="task.description"
/>

<label>Status</label>

<select
v-model="task.status"
>

<option value="TO_DO">TO_DO</option>

<option value="IN_PROGRESS">IN_PROGRESS</option>

<option value="DONE">DONE</option>

</select>

<label>Priority</label>

<select
v-model="task.priority"
>

<option value="LOW">LOW</option>

<option value="MEDIUM">MEDIUM</option>

<option value="HIGH">HIGH</option>

</select>

<hr>

<h2>Assigned user</h2>

<select
v-model="selectedUser"
>

<option value="">
Select user
</option>

<option
v-for="user in users"
:key="user.id"
:value="user.id"
>

{{ user.username }}

</option>

</select>

<button
@click="assign"
>

Assign user

</button>

<hr>

<h2>Comments</h2>

<div
v-if="comments.length==0"
>

No comments

</div>

<div
v-for="comment in comments"
:key="comment.id"
class="comment"
>

<b>

{{ comment.userName }}

</b>

<p>

{{ comment.text }}

</p>

</div>

<textarea
v-model="newComment"
placeholder="Write comment..."
/>

<button
@click="addNewComment"
>

Add comment

</button>

<hr>

<div class="buttons">

<button
@click="saveTask"
>

Save

</button>

<button
@click="back"
>

Back

</button>

</div>

</div>

</template>

<style scoped>
.container {
  max-width: 720px;
  margin: 40px auto;
  background: white;
  padding: 36px 44px;
  border-radius: 28px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.06);
}

.container h1 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 28px;
  background: linear-gradient(135deg, #6366f1, #8b5cf6);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.container label {
  margin-top: 16px;
  font-weight: 600;
  color: #334155;
}

.container input,
.container textarea,
.container select {
  margin-top: 4px;
}

hr {
  border: none;
  border-top: 2px solid #f1f5f9;
  margin: 24px 0;
}

h2 {
  font-size: 1.3rem;
  font-weight: 600;
  color: #0f172a;
  margin-bottom: 12px;
}

.assign-section {
  display: flex;
  gap: 12px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.assign-section select {
  flex: 1;
  min-width: 150px;
}

.assign-section button {
  background: #6366f1;
  color: white;
  padding: 10px 24px;
  border-radius: 30px;
  white-space: nowrap;
}

.assign-section button:hover {
  background: #4f46e5;
  box-shadow: 0 4px 14px rgba(99, 102, 241, 0.4);
}

.comment {
  background: #f8fafc;
  padding: 14px 18px;
  border-radius: 14px;
  margin-bottom: 12px;
}

.comment b {
  color: #0f172a;
  display: block;
  margin-bottom: 4px;
}

.comment p {
  margin: 0;
  color: #334155;
}

textarea {
  margin: 8px 0 10px 0;
}

.buttons {
  display: flex;
  gap: 14px;
  margin-top: 16px;
}

.buttons button:first-child {
  background: #6366f1;
  color: white;
  padding: 12px 32px;
  border-radius: 30px;
  font-weight: 600;
}

.buttons button:first-child:hover {
  background: #4f46e5;
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.4);
}

.buttons button:last-child {
  background: transparent;
  color: #64748b;
  border: 1px solid #e2e8f0;
  padding: 12px 28px;
  border-radius: 30px;
}

.buttons button:last-child:hover {
  background: #f1f5f9;
  transform: none;
  box-shadow: none;
}
</style>
