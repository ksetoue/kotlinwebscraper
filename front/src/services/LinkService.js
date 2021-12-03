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
    console.log(`${id} == ${data}`);
    return http.put(`/link/${id}`, data);
  }

  delete(id) {
    return http.delete(`/link/${id}`);
  }

  deleteAll() {
    return http.delete(`/link`);
  }

  findByTitle(title) {
    return http.get(`/link/find?title=${title}`);
  }
}

export default new LinkDataService();