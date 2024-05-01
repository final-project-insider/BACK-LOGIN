export default {
    setRooms(state, payload) {
        state.rooms = payload;
    },

    setEnteredRooms(state, payload) {
        state.enteredRooms = payload;
    }
}