<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { createTask, createTaskForUser } from "../api/taskService";
import { getAllUsers } from "../api/userService";
import { getUserRole } from "../utils/auth";

const router = useRouter();

const users = ref([]);
const selectedUser = ref("");

const task = ref({
    title: "",
    description: "",
    priority: "MEDIUM",
    status: "TO_DO"
});

const isAdmin = computed(() => getUserRole() === "ADMIN");

async function loadUsers() {
    const response = await getAllUsers();
    users.value = response.data;
}

async function create() {
    if (!task.value.title.trim()) {
        alert("Title is required");
        return;
    }

    if (isAdmin.value && selectedUser.value) {
        await createTaskForUser(selectedUser.value, task.value);
    } else {
        await createTask(task.value);
    }

    router.push("/");
}

function cancel() {
    router.push("/");
}

onMounted(() => {
    if (isAdmin.value) loadUsers();
});
</script>

<template>
    <div class="container">

        <h1>Create Task</h1>

        <label>Title</label>
        <input v-model="task.title" type="text" />

        <label>Description</label>
        <textarea v-model="task.description"></textarea>

        <label>Priority</label>
        <select v-model="task.priority">
            <option value="LOW">LOW</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="HIGH">HIGH</option>
        </select>

        <template v-if="isAdmin">
            <label>Assign User</label>
            <select v-model="selectedUser">
                <option value="">-- no user --</option>
                <option
                    v-for="user in users"
                    :key="user.id"
                    :value="user.id"
                >
                    {{ user.username }}
                </option>
            </select>
        </template>

        <div class="buttons">
            <button @click="create">Create</button>
            <button @click="cancel">Cancel</button>
        </div>

    </div>
</template>

<style scoped>
.container {
  max-width: 640px;
  margin: 40px auto;
  background: white;
  padding: 36px 40px;
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

.buttons {
  display: flex;
  gap: 14px;
  margin-top: 28px;
}

.buttons button:first-child {
  background: #6366f1;
  color: white;
  padding: 12px 32px;
  border-radius: 30px;
  font-weight: 600;
  flex: 1;
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
