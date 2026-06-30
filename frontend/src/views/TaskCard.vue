<script setup>
import StatusBadge from "../components/StatusBadge.vue";
import PriorityBadge from "../components/PriorityBadge.vue";

const props = defineProps({
    task: Object
});

const emit = defineEmits(["open", "delete"]);

function handleOpen() {
    emit("open", props.task.id);
}

function handleDelete() {
    emit("delete", props.task.id);
}
</script>

<template>
    <div class="card">
        <h3>{{ task.title }}</h3>
        <p>{{ task.description }}</p>

        <div class="badges">
            <StatusBadge :status="task.status" />
            <PriorityBadge :priority="task.priority" />
        </div>

        <p><b>User:</b> {{ task.userName ?? "Не назначен" }}</p>

        <div class="comment-wrapper">
            <span class="comment-icon">💬</span>
            <span class="comment-count">{{ (task.comments || []).length }}</span>
            <div class="comment-popup">
                <div v-if="task.comments && task.comments.length">
                    <div v-for="(comment, idx) in task.comments" :key="idx" class="comment-item">
                        {{ comment.text }}
                    </div>
                </div>
                <div v-else class="no-comments">Нет комментариев</div>
            </div>
        </div>

        <div class="buttons">
            <button @click="handleOpen">Open</button>
            <button @click="handleDelete">Delete</button>
        </div>
    </div>
</template>

<style scoped>
.card {
    background: white;
    padding: 18px;
    border-radius: 10px;
    box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 15px;
    position: relative;
}

.badges {
    display: flex;
    gap: 10px;
    margin: 10px 0;
}

.buttons {
    display: flex;
    gap: 10px;
    margin-top: 12px;
}

button {
    cursor: pointer;
    padding: 6px 16px;
}

.comment-wrapper {
    margin-top: 12px;
    padding-top: 10px;
    border-top: 1px solid #e9edf2;
    display: flex;
    align-items: center;
    gap: 6px;
    position: relative;
    cursor: default;
}

.comment-icon {
    font-size: 1.1rem;
}

.comment-count {
    font-size: 0.85rem;
    color: #475569;
    background: #f1f5f9;
    padding: 0 8px;
    border-radius: 12px;
    font-weight: 500;
}

.comment-popup {
    display: none;
    position: absolute;
    top: calc(100% + 8px);
    left: 0;
    background: white;
    border: 1px solid #d1d9e6;
    border-radius: 12px;
    padding: 12px 16px;
    min-width: 200px;
    max-width: 300px;
    max-height: 200px;
    overflow-y: auto;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
    z-index: 10;
    font-size: 0.9rem;
    color: #1e293b;
}

.comment-wrapper:hover .comment-popup {
    display: block;
}

.comment-item {
    padding: 4px 0;
    border-bottom: 1px solid #f1f5f9;
}

.comment-item:last-child {
    border-bottom: none;
}

.no-comments {
    color: #94a3b8;
    font-style: italic;
}
</style>