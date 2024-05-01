<template>
  <div class="wrap">
    <div class="container">
      <section class="room-list">
        <section class="room-tab">
          <div :class="activatedRoomTab" @click="activeTab('ALL')">전체 채팅 방</div>
          <div :class="activatedEnteredTab" @click="activeTab('MINE')">내가 속한 채팅 방</div>
        </section>
        <section style="width: 100%; height: calc(100% - 50px);  overflow: auto;">
          <keep-alive>
            <div style="overflow: hidden">
              <template v-if="isAllTab">
                <template v-for="room in rooms" :key="room">
                  <section style="padding: 5px;">
                    <div class="room-item" @click="openChatRoom(room)">
                      <p>{{room.name}}</p>
                    </div>
                  </section>
                </template>
              </template>
              <template v-else>
                <template v-for="room in enteredRooms" :key="room">
                  <section style="padding: 5px;">
                    <div class="room-item" @click="openChatRoom(room)">
                      <p>{{room.name}}</p>
                    </div>
                  </section>
                </template>
              </template>
            </div>
          </keep-alive>
        </section>
      </section>
      <section class="chat-area">
        <div class="chat-wrap">
          <section class="chat-header">
            <p>접속 중인 방 이름 : <span>{{enteredRoomName}}</span></p>
          </section>

          <section class="chat-list" v-if="!isLoading">

            <template v-for="message in messages" :key="message">
              <div style="width: 100%; min-height: 60px; margin: 10px; padding: 10px;">
                <div style="background: rgb(75 209 71 / 23%); min-height: 60px;">
                  {{message}}
                </div>

              </div>
            </template>

          </section>

          <base-spinner v-else/>


          <section class="chat-input-area">
            <input class="chat-input" type="text" @keyup.enter="send" v-model="textMessage"/>
            <button class="chat-input-send-btn" type="button"  @click="send">보내기</button>
          </section>

        </div>
      </section>
    </div>
  </div>
</template>

<script>

import { Client } from "@stomp/stompjs"

export default {
  name: 'App',

  data() {
    return {
      activeRoomTab: "ALL",
      rooms: [],
      enteredRooms: [],
      isLoading: false,
      currentRoom: null,

      websocketClient: null,

      textMessage: "",
      messages: [],
    };
  },

  computed: {
    activatedRoomTab() {

      return this.activeRoomTab === 'ALL' ? 'active-all-room': 'all-room';
    },

    activatedEnteredTab() {
      return this.activeRoomTab === 'MINE'? 'active-my-room': 'my-room';
    },

    isAllTab() {
      return this.activeRoomTab ==='ALL';
    },

    enteredRoomName() {
      if(!this.currentRoom) return "";

      return this.currentRoom.name;
    },
  },

  created() {
    this.loadRooms();
  },

  mounted() {
    this.rooms = this.$store.getters['member/rooms'];
    this.enteredRooms = this.$store.getters['member/enteredRooms'];
  },

  beforeUnmount() {
    this.disconnect()
  },

  methods: {
    async loadRooms() {
      try {
        await this.$store.dispatch('member/loadRooms')
        await this.$store.dispatch('member/loadEnteredRooms');

        this.rooms = this.$store.getters['member/rooms'];
        this.enteredRooms = this.$store.getters['member/enteredRooms'];
      } catch(error) {
        console.log(error)
      }
    },

    activeTab(activeTab) {
      this.activeRoomTab = activeTab;
    },

    async openChatRoom(room) {
      this.isLoading = true;

      await this.disconnect();
      this.currentRoom = room;
      this.clear();
      await this.connect();
    },

    disconnect() {
      if(this.websocketClient) {
        this.websocketClient.publish({
          destination: `/pub/room/${this.currentRoom.id}/leave`,
          body: JSON.stringify({message: `${this.textMessage}`, writer: "user1"}),
        });
        this.websocketClient.deactivate();
      }
    },

    connect() {
      //vue.config.js 참고

      const url = "ws://localhost:8080/wss/chatting";
      this.websocketClient = new Client({
        brokerURL: url,
        onConnect: () => {
          this.websocketClient.subscribe(`/sub/room/${this.currentRoom.id}`, msg => {
            this.messages.push(msg.body);

          })

          this.websocketClient.publish({
            destination: `/pub/room/${this.currentRoom.id}/entered`,
            body: JSON.stringify({message: `${this.textMessage}`, writer: "20240401"}),
          });

          this.isLoading = false;
        },

        onWebSocketError: () => {
          this.isLoading = false;
        },
      })

      this.websocketClient.activate();
    },

    send() {
      if(!this.websocketClient) return;

      this.websocketClient.publish({
        destination: `/pub/room/${this.currentRoom.id}`,
        body: JSON.stringify({message: `${this.textMessage}`, writer: "user1"}),
      });

    },

    clear() {
      this.textMessage = "";
      this.messages = [];
    },
  },
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");


* {
  box-sizing: border-box;
}
body {
  margin: 0;
}

html {
  font-family: "ROboto", sans-serif;
}

.wrap {
  width: 100%;
  height: 100%;
}

.container {
  width: 100%;
  height: 100%;
  padding: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.room-list {
  width: 400px;
  height: 600px;
  border: 1px solid black;
  margin-right: 2px;
}

.room-tab {
  width: 100%;
  height: 50px;
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-evenly;
  align-items: center
}

.all-room {
  border-radius: 5px;
  padding: 10px;
  background: rgba(154, 175, 154, 0.45);
  cursor: pointer;
}

.all-room:hover {
  background: rgba(0, 245, 0, 0.85);
}

.my-room {
  border-radius: 5px;
  padding: 10px;
  background: rgba(154, 175, 154, 0.45);
  cursor: pointer;
}
.active-my-room {
  border-radius: 5px;
  padding: 10px;
  background: rgba(0, 245, 0, 0.85);
  cursor: pointer;
}

.active-all-room {
  border-radius: 5px;
  padding: 10px;
  background: rgba(0, 245, 0, 0.85);
  cursor: pointer;
}

.my-room:hover {
  background: rgba(0, 245, 0, 0.85);
}

.room-item {
  width: 100%;
  height: 70px;
  background: #25ab25;
  border-radius: 10px;
  display: flex;
  align-content: center;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.room-item:hover {
  background: #1bce1b;
}

.room-item > p {
  font-size: 20px;
  font-weight: 800;
  color: white;
}

.chat-area {
  width: 600px;
  height: 600px;
  border: 1px solid black;
}

.chat-wrap {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.chat-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  border: 1px solid #c8c8e3;
  border-radius: 10px;
}

.chat-list {
  width: 100%;
  height: 100%;
  overflow: auto;
  margin-top: 20px;
  border-radius: 10px;
  border: 1px solid rgba(208,185,185,0.34);
  padding: 10px;
}

.chat-input-area {
  width: 100%;
  height: 200px;
  background: white;
  padding: 20px;
}

.chat-input {
  height: 100%;
  width: 80%;
  margin-right: 20px;
  border-radius: 10px;
}

.chat-input-send-btn {
  height: 100%;
  width: calc(20% - 20px);
  border-radius: 10px;
  border-style: none;
}
</style>
