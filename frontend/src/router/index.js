import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import TaskView from "../views/TaskView.vue";
import CreateTaskView from "../views/CreateTaskView.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView
        },
        {
            path: "/task/:id",
            name: "task",
            component: TaskView
        },
        {
            path: "/create",
            name: "create",
            component: CreateTaskView
        }
    ]
});

export default router;