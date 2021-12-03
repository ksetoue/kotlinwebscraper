<template>
  <div class="list row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <input type="text" class="form-control" placeholder="Search by title"
          v-model="title"/>
        <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="button"
            @click="searchTitle"
          >
            Search
          </button>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <h4>Source Link List</h4>
      <ul class="list-group">
        <li class="list-group-item"
          :class="{ active: index == currentIndex }"
          v-for="(link, index) in sourceLink"
          :key="index"
          @click="setActiveSourceLink(link, index)"
        >
          {{ link.title }}
          <router-link :to="'/links/' + link.id" class="badge badge-warning">Edit</router-link>
        </li>
      </ul>

      <button class="m-3 btn btn-sm btn-danger" @click="removeAllSourceLinks">
        Remove All
      </button>
    </div>
    <div class="col-md-6">
      <div v-if="currentLink">
        <h4>link</h4>
        <div>
          <label><strong>Title:</strong></label> {{ currentLink.title }}
        </div>
        <div>
          <label><strong>Source Links:</strong></label> {{ currentLink.source }}
        </div>
        <div>
          <label><strong> Links found in webpage:</strong></label>
          <ul class="list-group">
            <li class="list-group-item"
              v-for="(nestedLink, index) in currentLink.nestedLinks"
              :key="index"
            >
              <a :href=nestedLink.content target="_blank"><p>{{ nestedLink.content }}</p></a>
            </li>
          </ul>
        </div>
      </div>
      <div v-else>
        <br />
        <p>Please click on a link...</p>
      </div>
    </div>
  </div>
</template>

<script>
import LinkDataService from "../services/LinkService";

export default {
  name: "links-list",
  data() {
    return {
      sourceLink: [],
      currentLink: null,
      currentIndex: -1,
      title: ""
    };
  },
  methods: {
    retrieveSourceLinks() {
      LinkDataService.getAll()
        .then(response => {
          this.sourceLink = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },

    refreshList() {
      this.retrieveSourceLinks();
      this.currentLink = null;
      this.currentIndex = -1;
    },

    setActiveSourceLink(sourceLink, index) {
      this.currentLink = sourceLink;
      this.currentIndex = sourceLink ? index : -1;
    },

    removeAllSourceLinks() {
      LinkDataService.deleteAll()
        .then(response => {
          console.log(response.data);
          this.refreshList();
        })
        .catch(e => {
          console.log(e);
        });
    },
    
    searchTitle() {
      LinkDataService.findByTitle(this.title)
        .then(response => {
          this.sourceLink = response.data;
          this.setActiveSourceLink(null);
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.retrieveSourceLinks();
  }
};
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>