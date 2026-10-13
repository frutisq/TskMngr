<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import api from "@/api/axios";

const router = useRouter();

const username = ref("");
const password = ref("");
const error = ref("");
const loading = ref(false);

async function handleLogin() {
    error.value = "";
    loading.value = true;

    try {
        const { data } = await api.post("/auth/login", {
            username: username.value,
            password: password.value,
        });


        localStorage.setItem("token", data.token);
        localStorage.setItem("refreshToken", data.refreshToken);

        router.push("/");
    } catch (e) {
        if (e.response) {
            error.value = e.response.data?.message ?? "Неверный логин или пароль";
        } else {
            error.value = "Сервер недоступен";
        }
    } finally {
        loading.value = false;
    }
}

function goToRegister() {
    router.push("/registration");
}
</script>

<template>
    <div class="auth-container">
        <div class="auth-card">
            <h1>Вход</h1>

            <form @submit.prevent="handleLogin">
                <div class="form-group">
                    <label>Username</label>
                    <input
                        v-model="username"
                        type="text"
                        placeholder="Введите username"
                        autocomplete="username"
                        required
                    />
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input
                        v-model="password"
                        type="password"
                        placeholder="Введите пароль"
                        autocomplete="current-password"
                        required
                    />
                </div>

                <p v-if="error" class="error">{{ error }}</p>

                <button type="submit" :disabled="loading">
                    {{ loading ? "Вхожу..." : "Войти" }}
                </button>
            </form>

            <div class="register-link">
                <span>Нет аккаунта?</span>
                <button
                    class="secondary-button"
                    type="button"
                    @click="goToRegister"
                >
                    Зарегистрироваться
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.auth-container {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f1f5f9;
}

.auth-card {
    width: 380px;
    background: white;
    padding: 32px;
    border-radius: 16px;
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
}

.auth-card h1 {
    text-align: center;
    margin-bottom: 28px;
}

.form-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 18px;
}

.form-group label {
    margin-bottom: 6px;
    font-weight: 500;
}

.form-group input {
    padding: 10px 12px;
    border: 1px solid #cbd5e1;
    border-radius: 8px;
    font-size: 1rem;
}

.form-group input:focus {
    outline: none;
    border-color: #64748b;
}

.auth-card form button {
    width: 100%;
    padding: 11px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 1rem;
}

.auth-card form button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.error {
    color: #dc2626;
    font-size: 0.9rem;
    margin: -8px 0 12px;
}

.register-link {
    margin-top: 22px;
    padding-top: 18px;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.secondary-button {
    border: none;
    background: none;
    cursor: pointer;
    text-decoration: underline;
    font-size: 1rem;
}
</style>
