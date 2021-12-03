<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="title">Title</label>
        <input
          type="text"
          class="form-control"
          id="title"
          required
          v-model="link.title"
          name="title"
        />
      </div>

      <div class="form-group">
        <label for="source">Source</label>
        <input
          class="form-control"
          id="source"
          required
          v-model="link.source"
          name="source"
        />
      </div>

      <button @click="saveLink" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newLink">Add</button>
    </div>
  </div>
</template>

<script>
import LinkDataService from "../services/LinkService";

export default {
  name: "add-link",
  data() {
    return {
      link: {
        id: null,
        title: "",
        source: ""
      },
      submitted: false
    };
  },
  methods: {
    saveLink() {
      var data = {
        title: this.link.title,
        source: this.link.source
      };

      LinkDataService.create(data)
        .then(response => {
          this.link.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch(e => {
          console.log(e);
        });
    },
    
    newLink() {
      this.submitted = false;
      this.link = {};
    }
  }
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>