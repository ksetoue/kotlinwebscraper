import { createWebHistory, createRouter } from "vue-router";

const routes =  [
  {
    path: "/",
    alias: "/links",
    name: "links",
    component: () => import("./components/LinksList")
  },
  {
    path: "/links/:id",
    name: "link-details",
    component: () => import("./components/Link")
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/AddLink")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;