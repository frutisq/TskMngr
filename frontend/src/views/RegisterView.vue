<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();

const username = ref("");
const email = ref("");
const password = ref("");
const errorMessage = ref("");
const successMessage = ref("");
const loading = ref(false);

async function register() {
    errorMessage.value = "";
    successMessage.value = "";

    if (!username.value || !email.value || !password.value) {
        errorMessage.value = "Заполни все поля";
        return;
    }

    loading.value = true;

    try {
        await axios.post("/api/auth/register", {
            username: username.value,
            email: email.value,
            password: password.value
        });

        successMessage.value = "Регистрация успешна";

        setTimeout(() => {
            router.push("/login");
        }, 700);

    } catch (error) {
        console.error(error);

        if (error.response?.data) {
            errorMessage.value = error.response.data;
        } else {
            errorMessage.value = "Не удалось зарегистрировать пользователя";
        }
    } finally {
        loading.value = false;
    }
}

function goToLogin() {
    router.push("/login");
}
</script>

<template>
    <div class="auth-container">
        <div class="auth-card">
            <h1>Регистрация</h1>

            <form @submit.prevent="register">

                <div class="form-group">
                    <label>Username</label>
                    <input
                        v-model="username"
                        type="text"
                        placeholder="Введите username"
                    />
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input
                        v-model="email"
                        type="email"
                        placeholder="Введите email"
                    />
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input
                        v-model="password"
                        type="password"
                        placeholder="Введите пароль"
                    />
                </div>

                <p v-if="errorMessage" class="error">
                    {{ errorMessage }}
                </p>

                <p v-if="successMessage" class="success">
                    {{ successMessage }}
                </p>

                <button type="submit" :disabled="loading">
                    {{ loading ? "Регистрация..." : "Зарегистрироваться" }}
                </button>
            </form>

            <div class="login-link">
                <span>Уже есть аккаунт?</span>
                <button class="secondary-button" @click="goToLogin">
                    Войти
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

.auth-card form > button {
    width: 100%;
    padding: 11px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 1rem;
}

.auth-card form > button:disabled {
    cursor: not-allowed;
    opacity: 0.6;
}

.error {
    color: #dc2626;
    margin-bottom: 14px;
}

.success {
    color: #16a34a;
    margin-bottom: 14px;
}

.login-link {
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
}
</style>
