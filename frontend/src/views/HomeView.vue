<script setup>
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { getAllTasks, deleteTask } from "../api/taskService";
import { getComments } from "../api/commentService";
import TaskCard from "./TaskCard.vue";

const router = useRouter();
const tasks = ref([]);

async function loadTasks() {
    try {
        const response = await getAllTasks();
        tasks.value = response.data;
        await loadCommentsForTasks();
    } catch (error) {
        console.error(error);
        alert("Не удалось загрузить задачи");
    }
}

async function loadCommentsForTasks() {
    const commentPromises = tasks.value.map(async (task) => {
        try {
            const response = await getComments(task.id);
            task.comments = response.data;
        } catch (error) {
            console.error(`Ошибка загрузки комментариев для задачи ${task.id}`, error);
            task.comments = [];
        }
    });
    await Promise.all(commentPromises);
}

async function removeTask(id) {
    if (!confirm("Удалить задачу?")) return;
    await deleteTask(id);
    await loadTasks();
}

function openTask(id) {
    router.push(`/task/${id}`);
}

function createTask() {
    router.push("/create");
}

const tasksByStatus = computed(() => {
    const groups = { todo: [], inProgress: [], done: [] };
    tasks.value.forEach(task => {
        const status = (task.status || '').toUpperCase().trim();
        if (status === 'TODO' || status === 'TO DO') {
            groups.todo.push(task);
        } else if (status === 'IN_PROGRESS' || status === 'IN PROGRESS') {
            groups.inProgress.push(task);
        } else if (status === 'DONE') {
            groups.done.push(task);
        } else {
            groups.todo.push(task);
        }
    });
    return groups;
});

onMounted(() => loadTasks());
</script>

<template>
    <div class="container">
        <h1>Task Manager</h1>
        <button @click="createTask">New Task</button>

        <div class="board">
            <!-- TO DO -->
            <div class="column">
                <h2>To Do</h2>
                <span class="count">{{ tasksByStatus.todo.length }}</span>
                <TaskCard
                    v-for="task in tasksByStatus.todo"
                    :key="task.id"
                    :task="task"
                    @open="openTask"
                    @delete="removeTask"
                />
                <div v-if="tasksByStatus.todo.length === 0" class="empty">Нет задач</div>
            </div>

            <!-- IN PROGRESS -->
            <div class="column">
                <h2>In Progress</h2>
                <span class="count">{{ tasksByStatus.inProgress.length }}</span>
                <TaskCard
                    v-for="task in tasksByStatus.inProgress"
                    :key="task.id"
                    :task="task"
                    @open="openTask"
                    @delete="removeTask"
                />
                <div v-if="tasksByStatus.inProgress.length === 0" class="empty">Нет задач</div>
            </div>

            <!-- DONE -->
            <div class="column">
                <h2>Done</h2>
                <span class="count">{{ tasksByStatus.done.length }}</span>
                <TaskCard
                    v-for="task in tasksByStatus.done"
                    :key="task.id"
                    :task="task"
                    @open="openTask"
                    @delete="removeTask"
                />
                <div v-if="tasksByStatus.done.length === 0" class="empty">Нет задач</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.container {
    width: 900px;
    margin: 40px auto;
}

.board {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
    margin-top: 30px;
}

.column {
    background: #f1f5f9;
    border-radius: 16px;
    padding: 16px 14px;
    min-height: 300px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.column h2 {
    font-size: 1.2rem;
    display: inline-block;
    margin-right: 10px;
    margin-bottom: 16px;
}

.count {
    background: #e2e8f0;
    padding: 2px 12px;
    border-radius: 30px;
    font-size: 0.8rem;
    font-weight: 500;
}

.empty {
    color: #94a3b8;
    text-align: center;
    padding: 30px 0;
    font-size: 0.95rem;
}

@media (max-width: 700px) {
    .board {
        grid-template-columns: 1fr;
        gap: 30px;
    }
    .container {
        width: 100%;
        padding: 0 15px;
    }
}
</style>