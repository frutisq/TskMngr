<template>
    <router-view />
</template>

<script setup>
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  background: #f8fafc;
  color: #0f172a;
  min-height: 100vh;
  padding: 20px;
}

button {
  font-family: inherit;
  border: none;
  border-radius: 8px;
  padding: 8px 18px;
  font-weight: 500;
  font-size: 0.9rem;
  transition: all 0.2s ease;
  background: #e2e8f0;
  color: #1e293b;
  cursor: pointer;
}

button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

button.primary {
  background: #6366f1;
  color: white;
}

button.primary:hover {
  background: #4f46e5;
  box-shadow: 0 4px 14px rgba(99, 102, 241, 0.4);
}

button.danger {
  background: #f43f5e;
  color: white;
}

button.danger:hover {
  background: #e11d48;
  box-shadow: 0 4px 14px rgba(244, 63, 94, 0.4);
}

input, textarea, select {
  font-family: inherit;
  font-size: 0.95rem;
  padding: 10px 14px;
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  background: white;
  transition: border 0.2s, box-shadow 0.2s;
  width: 100%;
}

input:focus, textarea:focus, select:focus {
  outline: none;
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.2);
}

textarea {
  resize: vertical;
  min-height: 100px;
}

label {
  font-weight: 600;
  font-size: 0.9rem;
  color: #334155;
  margin-bottom: 4px;
}
</style>