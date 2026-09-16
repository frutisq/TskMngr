<script setup>
import { onMounted, ref, computed, nextTick } from "vue";
import { useRouter } from "vue-router";
import Sortable from "sortablejs";

import { getAllTasks, deleteTask, updateTask } from "../api/taskService";
import { getComments } from "../api/commentService";
import TaskCard from "./TaskCard.vue";

const router = useRouter();
const tasks = ref([]);

async function loadTasks() {
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

async function handleLogout() {
  try {
    await axios.post('/api/auth/logout');
  } catch (error) {
    console.error('Ошибка при выходе на сервере:', error);
  } finally {
    localStorage.removeItem('token');
    router.push('/login');
  }
}

const tasksByStatus = computed(() => {
    const groups = { todo: [], inProgress: [], done: [] };

    tasks.value.forEach(task => {
        const status = (task.status || '').toUpperCase().trim();

        if (status === 'TO_DO' || status === 'TODO' || status === 'TO DO') {
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

/* ---------------- DRAG & DROP ---------------- */

async function changeStatus(task, status) {
    task.status = status;
    await updateTask(task.id, task);
}

function initDrag() {
    const createSortable = (id, status) => {
        const el = document.getElementById(id);
        if (!el) return;

        new Sortable(el, {
            group: "tasks",
            animation: 150,
            onAdd: async (evt) => {
                const taskId = evt.item.dataset.id;
                const task = tasks.value.find(t => t.id == taskId);
                if (!task) return;
                await changeStatus(task, status);
            }
        });
    };

    createSortable("todo", "TO_DO");
    createSortable("inProgress", "IN_PROGRESS");
    createSortable("done", "DONE");
}

onMounted(() => loadTasks());
</script>

<template>
    <div class="container">
        <div class="header">
            <h1>📋 Task Manager</h1>
            <button @click="createTask">New Task</button>
            <button @click="handleLogout">Выхади</button>
        </div>

        <div class="board">
            <!-- TO DO -->
            <div class="column">
                <div class="column-header">
                    <h2>📝 To Do</h2>
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

            <!-- IN PROGRESS -->
            <div class="column">
                <div class="column-header">
                    <h2>⚡ In Progress</h2>
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

            <!-- DONE -->
            <div class="column">
                <div class="column-header">
                    <h2>✅ Done</h2>
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
</template>

<style scoped>
.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 32px;
}

.header h1 {
    font-size: 2.2rem;
    font-weight: 700;
    background: linear-gradient(135deg, #6366f1, #8b5cf6);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    margin: 0;
}

.header button {
    background: #6366f1;
    color: white;
    padding: 10px 24px;
    font-size: 1rem;
    border-radius: 30px;
    box-shadow: 0 4px 14px rgba(99, 102, 241, 0.35);
    border: none;
    cursor: pointer;
    transition: all 0.2s ease;
    font-weight: 500;
}

.header button:hover {
    background: #4f46e5;
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(99, 102, 241, 0.45);
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
    transition: box-shadow 0.3s;
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

/* Цветовые акценты для колонок */
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
    .header {
        flex-direction: column;
        align-items: stretch;
        gap: 16px;
    }
}

@media (max-width: 600px) {
    .board {
        grid-template-columns: 1fr;
    }
}
</style>
