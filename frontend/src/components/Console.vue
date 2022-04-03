<template>
  <!--
  Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
  This is a confidential file. Without a written licence by Boltzbit Limited,
  any copy, distribution or modification of this file is strictly prohibited.
  -->

  <div class="console">
    <h1>{{ msg }}</h1>

    <button @click="listWorkerStatus">List Workers</button>
    <form id="queryForm" @submit.prevent>
      <select v-model="workerType">
        <option value="GPU">GPU</option>
        <option value="CPU">CPU</option>
      </select>
      <button @click="requestWorker">Request Worker</button>
    </form>

    <div id="responseArea">
      <!-- [Boltzbit] list worker status (stored in listData) in the example format as follows

      worker1.id | worker1.workerType | worker1.status
      worker2.id | worker2.workerType | worker2.status
      ...

      -->
      <ul>
        <li v-for="(worker, index) in listData" :key="index">
          {{ worker.id }} | {{ worker.workerType }} | {{ worker.status }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Console",
  props: {
    msg: String
  },
  data() {
    return {
      listData: [{ id: "workerId", workerType: "Type", status: "Status" }],
      workerType: "GPU"
    };
  },
  methods: {
    async listWorkerStatus() {
      const params = {
        type: this.workerType
      };
      const response = await axios.get("/api/worker/list", {
        params
      });
      console.log(response);
      const responseData = response.data;
      if (responseData.code === 0) {
        this.listData = JSON.parse(responseData.objectJSON);
      }
      console.log("listWorkerStatus called");
    },
    async requestWorker() {
      // [Boltzbit] request a worker by calling backend API
      const params = {
        type: this.workerType
      };
      const response = await axios.post("/api/worker/request", null, {
        params
      });
      console.log(response);
      const responseData = response.data;
      if (responseData.code === 0) {
        alert(responseData.objectJSON);
      } else if (responseData.code === 1) {
        alert("No Idle workers available");
      }
    }

    // [Boltzbit] regularly refresh worker status
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
