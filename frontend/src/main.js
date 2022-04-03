/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */

import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
