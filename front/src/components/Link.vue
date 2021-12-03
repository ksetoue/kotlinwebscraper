<template>
  <div v-if="currentLink" class="edit-form">
    <h4>Link</h4>
    <form>
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title"
          v-model="currentLink.title"
        />
      </div>
      <div class="form-group">
        <label for="source">Souce link</label>
        <input type="text" class="form-control" id="source"
          v-model="currentLink.source"
        />
      </div>
    </form>

    <button class="badge badge-danger mr-2"
      @click="deleteLink"
    >
      Delete
    </button>

    <button type="submit" class="badge badge-success"
      @click="updateLink"
    >
      Update
    </button>
    <p>{{ message }}</p>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Link...</p>
  </div>
</template>

<script>
import LinkDataService from "../services/LinkService";

export default {
  name: "link",
  data() {
    return {
      currentLink: null,
      message: ''
    };
  },
  methods: {
    getLink(id) {
      LinkDataService.get(id)
        .then(response => {
          this.currentLink = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },

    updateLink() {
      LinkDataService.update(this.currentLink.id, this.currentLink)
        .then(response => {
          console.log(response.data);
          this.message = 'The link was updated successfully!';
        })
        .catch(e => {
          console.log(e);
        });
    },

    deleteLink() {
      LinkDataService.delete(this.currentLink.id)
        .then(response => {
          console.log(response.data);
          this.$router.push({ name: "links" });
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.message = '';
    this.getLink(this.$route.params.id);
  }
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>