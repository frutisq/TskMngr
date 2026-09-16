<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

function goToRegister() {
    router.push("/registration");
}
</script>

<template>
    <div class="auth-container">
        <div class="auth-card">
            <h1>Вход</h1>

            <!--
                Пока логин обрабатывает Spring Security.
                Поэтому форма отправляется именно на /login.
            -->
            <form action="/api/auth/login" method="post">

                <div class="form-group">
                    <label>Username</label>

                    <input
                        type="text"
                        name="username"
                        placeholder="Введите username"
                        required
                    />
                </div>

                <div class="form-group">
                    <label>Password</label>

                    <input
                        type="password"
                        name="password"
                        placeholder="Введите пароль"
                        required
                    />
                </div>

                <button type="submit">
                    Войти
                </button>
            </form>

            <div class="register-link">
                <span>Нет аккаунта?</span>

                <button
                    class="secondary-button"
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
}
</style>
