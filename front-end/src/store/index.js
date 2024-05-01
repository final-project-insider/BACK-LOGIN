import { createStore } from "vuex";
import userModule from "./modules/user"
const store = createStore({
    modules: {
        user: userModule
    }
});

export default store;
