<script setup>
import { onMounted, onBeforeUnmount, ref, computed, nextTick } from "vue";
import { useRouter } from "vue-router";
import Sortable from "sortablejs";

import { getAllTasks, deleteTask, updateTaskStatus } from "../api/taskService";
import { getComments } from "../api/commentService";
import TaskCard from "./TaskCard.vue";

const router = useRouter();
const tasks = ref([]);
const sortableInstances = [];

const isAuthenticated = computed(() => !!localStorage.getItem("token"));

async function loadTasks() {
    if (!isAuthenticated.value) return;

    try {
        const response = await getAllTasks();
        tasks.value = response.data;

        await loadCommentsForTasks();

        await nextTick();
        initDrag();
    } catch (error) {
        console.error(error);
        alert("Не удалось загрузить задачи");
    }
}

async function loadCommentsForTasks() {
    await Promise.all(
        tasks.value.map(async (task) => {
            try {
                const response = await getComments(task.id);
                task.comments = response.data;
            } catch (error) {
                console.error(
                    `Ошибка загрузки комментариев для задачи ${task.id}`,
                    error
                );
                task.comments = [];
            }
        })
    );
}

async function removeTask(id) {
    if (!confirm("Удалить задачу?")) return;

    try {
        await deleteTask(id);
        await loadTasks();
    } catch (error) {
        console.error(error);
        alert("Не удалось удалить задачу");
    }
}

function openTask(id) {
    router.push(`/task/${id}`);
}

function createTask() {
    router.push("/create");
}

function goLogin() {
    router.push("/login");
}

function goRegister() {
    router.push("/registration");
}

async function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    await router.push("/login");
}

const tasksByStatus = computed(() => {
    const groups = {
        todo: [],
        inProgress: [],
        done: []
    };

    tasks.value.forEach((task) => {
        const status = (task.status || "").toUpperCase().trim();

        if (status === "TO_DO" || status === "TODO" || status === "TO DO") {
            groups.todo.push(task);
        } else if (status === "IN_PROGRESS" || status === "IN PROGRESS") {
            groups.inProgress.push(task);
        } else if (status === "DONE") {
            groups.done.push(task);
        } else {
            groups.todo.push(task);
        }
    });

    return groups;
});

async function changeStatus(task, newStatus) {
    const previousStatus = task.status;
    if (previousStatus === newStatus) return;

    try {
        await updateTaskStatus(task.id, { status: newStatus });
        task.status = newStatus;
    } catch (error) {
        console.error("Не удалось изменить статус задачи:", error);
        alert("Не удалось изменить статус задачи. Доска будет обновлена.");
        await loadTasks();
    }
}

function initDrag() {
    sortableInstances.forEach((instance) => instance.destroy());
    sortableInstances.length = 0;

    const createSortable = (id, status) => {
        const element = document.getElementById(id);
        if (!element) return;

        const instance = new Sortable(element, {
            group: "tasks",
            animation: 150,
            onAdd: async (event) => {
                const taskId = event.item.dataset.id;
                const task = tasks.value.find(
                    (item) => String(item.id) === String(taskId)
                );

                if (!task) {
                    await loadTasks();
                    return;
                }

                await changeStatus(task, status);
            }
        });

        sortableInstances.push(instance);
    };

    createSortable("todo", "TO_DO");
    createSortable("inProgress", "IN_PROGRESS");
    createSortable("done", "DONE");
}

onMounted(() => loadTasks());

onBeforeUnmount(() => {
    sortableInstances.forEach((instance) => instance.destroy());
    sortableInstances.length = 0;
});
</script>

<template>
    <div>

        <nav class="navbar">
            <div class="navbar-brand" @click="router.push('/')">
                TskMngr
            </div>

            <div class="navbar-actions">
                <template v-if="isAuthenticated">
                    <button class="nav-btn primary" @click="createTask">
                        + Создать задачу
                    </button>
                    <button class="nav-btn ghost" @click="handleLogout">
                        Выйти
                    </button>
                </template>

                <template v-else>
                    <button class="nav-btn ghost" @click="goLogin">
                        Войти
                    </button>
                    <button class="nav-btn primary" @click="goRegister">
                        Регистрация
                    </button>
                </template>
            </div>
        </nav>

        <div v-if="!isAuthenticated" class="guest">
            <h2>Вы не авторизованы</h2>
            <p>Войдите, чтобы просматривать и создавать задачи.</p>
            <button class="nav-btn primary" @click="goLogin">Войти</button>
        </div>

        <div v-else class="container">
            <div class="board">
                <div class="column">
                    <div class="column-header">
                        <h2>To Do</h2>
                        <span class="count">{{ tasksByStatus.todo.length }}</span>
                    </div>

                    <div id="todo">
                        <TaskCard
                            v-for="task in tasksByStatus.todo"
                            :key="task.id"
                            :task="task"
                            :data-id="task.id"
                            @open="openTask"
                            @delete="removeTask"
                        />
                    </div>

                    <div v-if="tasksByStatus.todo.length === 0" class="empty">
                        Нет задач
                    </div>
                </div>

                <div class="column">
                    <div class="column-header">
                        <h2>In Progress</h2>
                        <span class="count">{{ tasksByStatus.inProgress.length }}</span>
                    </div>

                    <div id="inProgress">
                        <TaskCard
                            v-for="task in tasksByStatus.inProgress"
                            :key="task.id"
                            :task="task"
                            :data-id="task.id"
                            @open="openTask"
                            @delete="removeTask"
                        />
                    </div>

                    <div v-if="tasksByStatus.inProgress.length === 0" class="empty">
                        Нет задач
                    </div>
                </div>

                <div class="column">
                    <div class="column-header">
                        <h2>Done</h2>
                        <span class="count">{{ tasksByStatus.done.length }}</span>
                    </div>

                    <div id="done">
                        <TaskCard
                            v-for="task in tasksByStatus.done"
                            :key="task.id"
                            :task="task"
                            :data-id="task.id"
                            @open="openTask"
                            @delete="removeTask"
                        />
                    </div>

                    <div v-if="tasksByStatus.done.length === 0" class="empty">
                        Нет задач
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

.navbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 14px 32px;
    background: white;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
    position: sticky;
    top: 0;
    z-index: 10;
}

.navbar-brand {
    font-size: 1.4rem;
    font-weight: 700;
    background: linear-gradient(135deg, #6366f1, #8b5cf6);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    cursor: pointer;
}

.navbar-actions {
    display: flex;
    gap: 12px;
}

.nav-btn {
    padding: 9px 20px;
    font-size: 0.95rem;
    border-radius: 30px;
    border: none;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.2s ease;
}

.nav-btn.primary {
    background: #6366f1;
    color: white;
    box-shadow: 0 4px 14px rgba(99, 102, 241, 0.35);
}

.nav-btn.primary:hover {
    background: #4f46e5;
    transform: translateY(-1px);
}

.nav-btn.ghost {
    background: transparent;
    color: #475569;
    border: 1px solid #e2e8f0;
}

.nav-btn.ghost:hover {
    background: #f1f5f9;
}

.guest {
    max-width: 480px;
    margin: 120px auto;
    text-align: center;
    background: white;
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.06);
}

.guest h2 {
    margin-bottom: 12px;
    color: #1e293b;
}

.guest p {
    margin-bottom: 24px;
    color: #64748b;
}


.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 28px 20px;
}

.board {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
}

.column {
    background: #ffffff;
    border-radius: 20px;
    padding: 20px 16px;
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.04);
    min-height: 400px;
    display: flex;
    flex-direction: column;
}

.column:hover {
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.07);
}

.column-header {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 20px;
    padding-bottom: 12px;
    border-bottom: 2px solid #f1f5f9;
}

.column-header h2 {
    font-size: 1.2rem;
    font-weight: 600;
    margin: 0;
    color: #1e293b;
}

.column-header .count {
    background: #eef2ff;
    color: #4f46e5;
    font-weight: 600;
    padding: 2px 12px;
    border-radius: 30px;
    font-size: 0.8rem;
    margin-left: auto;
}

.column:nth-child(1) .column-header {
    border-bottom-color: #fcd34d;
}

.column:nth-child(2) .column-header {
    border-bottom-color: #60a5fa;
}

.column:nth-child(3) .column-header {
    border-bottom-color: #34d399;
}

#todo,
#inProgress,
#done {
    flex: 1;
    min-height: 200px;
    position: relative;
    z-index: 1;
}

.empty {
    color: #94a3b8;
    text-align: center;
    padding: 40px 0;
    font-size: 0.95rem;
    font-style: italic;
    background: #f8fafc;
    border-radius: 12px;
    margin-top: 10px;
}

@media (max-width: 900px) {
    .board {
        grid-template-columns: 1fr 1fr;
    }
}

@media (max-width: 600px) {
    .board {
        grid-template-columns: 1fr;
    }
    .navbar {
        padding: 12px 16px;
    }
    .navbar-actions {
        gap: 8px;
    }
}
</style>
