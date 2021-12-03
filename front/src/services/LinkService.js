import http from "../http-common";

class LinkDataService {
  getAll() {
    return http.get("/link");
  }

  get(id) {
    return http.get(`/link/${id}`);
  }

  create(data) {
    return http.post("/link", data);
  }

  update(id, data) {
    return http.put(`/link/${id}`, data);
  }

  // delete(id) {
  //   return http.delete(`/tutorials/${id}`);
  // }

  // deleteAll() {
  //   return http.delete(`/tutorials`);
  // }

  // findByTitle(title) {
  //   return http.get(`/tutorials?title=${title}`);
  // }
}

export default new LinkDataService();