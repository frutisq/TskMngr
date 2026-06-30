<script setup>
defineProps({
    status: String
});
</script>

<template>
    <span class="badge" :class="status">
        {{ status }}
    </span>
</template>

<style scoped>

.badge{
    padding:4px 10px;
    border-radius:20px;
    color:white;
    font-size:12px;
    font-weight:bold;
}

.TO_DO{
    background:#64748b;
}

.IN_PROGRESS{
    background:#2563eb;
}

.DONE{
    background:#16a34a;
}

</style>