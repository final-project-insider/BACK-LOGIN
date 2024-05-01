import mutations from "./mutations";
import actions from "./actions";
import getters from "./getters";

export default {
    namespaced: true,
    state() {
        return {
            rooms: [],
            enteredRooms: [],
            memberId: 202401001,
        };
    },
    mutations,
    getters,
    actions,
}