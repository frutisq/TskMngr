<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { createTask } from "../api/taskService";

const router = useRouter();

const task = ref({
    title: "",
    description: "",
    status: "TO_DO",
    priority: "LOW"
});

async function saveTask() {

    if (!task.value.title.trim()) {
        alert("Введите название");
        return;
    }

    try {

        await createTask(task.value);

        router.push("/");

    } catch (e) {

        console.error(e);

        alert("Ошибка создания задачи");

    }

}
</script>

<template>

<div class="container">

    <h1>Create task</h1>

    <label>Title</label>

    <input
        v-model="task.title"
    >

    <label>Description</label>

    <textarea
        v-model="task.description"
    />

    <label>Status</label>

    <select v-model="task.status">

        <option value="TO_DO">To Do</option>

        <option value="IN_PROGRESS">In Progress</option>

        <option value="DONE">Done</option>

    </select>

    <label>Priority</label>

    <select v-model="task.priority">

        <option value="LOW">Low</option>

        <option value="MEDIUM">Medium</option>

        <option value="HIGH">High</option>

    </select>

    <div class="buttons">

        <button @click="saveTask">
            Save
        </button>

        <button @click="$router.push('/')">
            Cancel
        </button>

    </div>

</div>

</template>

<style scoped>

.container{

    width:600px;
    margin:40px auto;
    background:white;
    padding:30px;
    border-radius:10px;

}

label{

    display:block;
    margin-top:20px;
    margin-bottom:5px;
    font-weight:bold;

}

input,
textarea,
select{

    width:100%;
    padding:10px;
    font-size:16px;
    box-sizing:border-box;

}

textarea{

    height:120px;
    resize:vertical;

}

.buttons{

    margin-top:30px;
    display:flex;
    gap:10px;

}

button{

    padding:10px 20px;

}

</style>