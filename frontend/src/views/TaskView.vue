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

    if (!selectedUser.value) {

        alert("Assign user first");

        return;
    }

    await addComment(
        task.value.id,
        selectedUser.value,
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

.container{

width:700px;
margin:40px auto;
display:flex;
flex-direction:column;
gap:12px;

}

input,
textarea,
select{

padding:10px;
font-size:15px;

}

textarea{

min-height:100px;

}

.comment{

padding:12px;
background:#f3f4f6;
border-radius:8px;
margin-bottom:10px;

}

.buttons{

display:flex;
gap:10px;

}

button{

padding:10px 18px;
cursor:pointer;

}

</style>