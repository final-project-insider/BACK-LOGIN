export default {
    async loadRooms(context) {
        try {
            let response = await fetch(`/api/rooms`, {
                method: "GET"
            });

            if (!response.ok) {
                throw new Error('Failed to fetch rooms');
            }

            const responseData = await response.json();
            const rooms = responseData.map(data => ({
                id: data.roomId,
                name: data.name
            }));

            context.commit('setRooms', rooms);
        } catch (error) {
            console.error('Error loading rooms:', error);
        }
    },

    async loadEnteredRooms(context) {
        try {
            const memberId = 202401001;

            let response = await fetch(`/api/rooms/joined?memberId=${memberId}`, {
                method: "GET",
            });

            if (!response.ok) {
                throw new Error('Failed to fetch entered rooms');
            }

            const responseData = await response.json();
            const enteredRooms = responseData.map(data => ({
                id: data.roomId,
                name: data.roomName
            }));

            context.commit('setEnteredRooms', enteredRooms);
        } catch (error) {
            console.error('Error loading entered rooms:', error);
        }
    }
}
