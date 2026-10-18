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
    <div class="card" :data-id="task.id">
        <h3>{{ task.title }}</h3>
        <p>{{ task.description }}</p>

        <div class="badges">
            <StatusBadge :status="task.status" />
            <PriorityBadge :priority="task.priority" />
        </div>

        <p class="user-info"><b>👤User:</b> {{ task.userName ?? "Не назначен" }}</p>

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
            <button @click="handleOpen">Открыть</button>
            <button @click="handleDelete">Удалить</button>
        </div>
    </div>
</template>

<style scoped>
.card {
    background: #ffffff;
    border-radius: 16px;
    padding: 18px 20px;
    margin-bottom: 14px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    border: 1px solid #f1f5f9;
    transition: all 0.2s ease;
    cursor: grab;
    position: relative;
    z-index: 1;
}

.card:hover {
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
    border-color: #e2e8f0;
    transform: translateY(-2px);
    z-index: 2;
}

.card:active {
    cursor: grabbing;
}

.card h3 {
    font-size: 1.1rem;
    font-weight: 600;
    color: #0f172a;
    margin: 0 0 6px 0;
}

.card p {
    color: #475569;
    font-size: 0.9rem;
    margin: 4px 0 8px 0;
    line-height: 1.4;
}

.badges {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
    margin: 8px 0 6px 0;
}

.user-info {
    font-size: 0.85rem;
    color: #64748b;
    margin: 6px 0;
}

.comment-wrapper {
    margin-top: 12px;
    padding-top: 12px;
    border-top: 1px solid #f1f5f9;
    display: flex;
    align-items: center;
    gap: 6px;
    position: relative;
    cursor: default;
    z-index: 10;
}

.comment-icon {
    font-size: 1.1rem;
}

.comment-count {
    background: #f1f5f9;
    color: #475569;
    font-size: 0.75rem;
    font-weight: 600;
    padding: 0 10px;
    border-radius: 30px;
    line-height: 22px;
}

.comment-popup {
    display: none;
    position: absolute;
    bottom: calc(100% + 8px);
    left: 0;
    background: white;
    border: 1px solid #e2e8f0;
    border-radius: 16px;
    padding: 12px 16px;
    min-width: 200px;
    max-width: 300px;
    max-height: 200px;
    overflow-y: auto;
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
    z-index: 9999;
    font-size: 0.9rem;
    color: #1e293b;
}

.comment-wrapper:hover .comment-popup {
    display: block;
}

.comment-item {
    padding: 6px 0;
    border-bottom: 1px solid #f1f5f9;
}

.comment-item:last-child {
    border-bottom: none;
}

.no-comments {
    color: #94a3b8;
    font-style: italic;
}

.buttons {
    display: flex;
    gap: 8px;
    margin-top: 12px;
}

.buttons button {
    padding: 6px 16px;
    font-size: 0.8rem;
    border-radius: 20px;
    background: #f1f5f9;
    color: #1e293b;
    border: none;
    cursor: pointer;
    transition: all 0.2s ease;
}

.buttons button:first-child {
    background: #eef2ff;
    color: #4f46e5;
}

.buttons button:first-child:hover {
    background: #c7d2fe;
}

.buttons button:last-child {
    background: #fee2e2;
    color: #dc2626;
}

.buttons button:last-child:hover {
    background: #fecaca;
}
</style>
