<script setup>
defineProps({
    priority: String
});
</script>

<template>

<span class="badge" :class="priority">

{{ priority }}

</span>

</template>

<style scoped>

.badge{

padding:4px 10px;
border-radius:20px;
color:white;
font-size:12px;

}

.LOW{

background:#22c55e;

}

.MEDIUM{

background:#eab308;

}

.HIGH{

background:#ef4444;

}

</style>