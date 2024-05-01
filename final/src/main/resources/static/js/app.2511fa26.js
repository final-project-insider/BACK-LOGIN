(function(){"use strict";var e={2913:function(e,t,o){var s=o(9242),n=o(3396);const i=e=>((0,n.dD)("data-v-2389dacc"),e=e(),(0,n.Cn)(),e),r={class:"spinner"},a=i((()=>(0,n._)("div",{class:"lds-roller"},[(0,n._)("div"),(0,n._)("div"),(0,n._)("div"),(0,n._)("div"),(0,n._)("div"),(0,n._)("div"),(0,n._)("div"),(0,n._)("div")],-1))),c=[a];function d(e,t){return(0,n.wg)(),(0,n.iD)("div",r,c)}var l=o(89);const u={},m=(0,l.Z)(u,[["render",d],["__scopeId","data-v-2389dacc"]]);var h=m,p=o(65),v={setRooms(e,t){e.rooms=t},setEnteredRooms(e,t){e.enteredRooms=t}},b=(o(560),{async loadRooms(e){let t=await fetch("/api/v1/room",{method:"GET"});const o=await t.json();t.ok;const s=[];for(const n of o.data){const e={id:n.roomId,name:n.name};s.push(e)}e.commit("setRooms",s)},async loadEnteredRooms(e){const t=2;let o=await fetch(`/api/v1/room/joined?userId=${t}`,{method:"GET"});const s=await o.json();o.ok;const n=[];for(const i of s.data){const e={id:i.roomId,name:i.roomName};n.push(e)}e.commit("setEnteredRooms",n)}}),f={rooms(e){return e.rooms},enteredRooms(e){return e.enteredRooms}},g={namespaced:!0,state(){return{rooms:[],enteredRooms:[],userId:2}},mutations:v,getters:f,actions:b};const w=(0,p.MT)({modules:{user:g}});var R=w,y=o(7139);const _={class:"wrap"},k={class:"container"},C={class:"room-list"},x={class:"room-tab"},T={style:{width:"100%",height:"calc(100% - 50px)",overflow:"auto"}},O={style:{overflow:"hidden"}},L=["onClick"],$=["onClick"],E={class:"chat-area"},j={class:"chat-wrap"},D={class:"chat-header"},M={key:0,class:"chat-list"},N={style:{background:"rgb(75 209 71 / 23%)","min-height":"60px"}},A={class:"chat-input-area"};function I(e,t,o,i,r,a){const c=(0,n.up)("base-spinner");return(0,n.wg)(),(0,n.iD)("div",_,[(0,n._)("div",k,[(0,n._)("section",C,[(0,n._)("section",x,[(0,n._)("div",{class:(0,y.C_)(a.activatedRoomTab),onClick:t[0]||(t[0]=e=>a.activeTab("ALL"))},"전체 채팅 방",2),(0,n._)("div",{class:(0,y.C_)(a.activatedEnteredTab),onClick:t[1]||(t[1]=e=>a.activeTab("MINE"))},"내가 속한 채팅 방",2)]),(0,n._)("section",T,[((0,n.wg)(),(0,n.j4)(n.Ob,null,[(0,n._)("div",O,[a.isAllTab?((0,n.wg)(!0),(0,n.iD)(n.HY,{key:0},(0,n.Ko)(r.rooms,(e=>((0,n.wg)(),(0,n.iD)("section",{key:e,style:{padding:"5px"}},[(0,n._)("div",{class:"room-item",onClick:t=>a.openChatRoom(e)},[(0,n._)("p",null,(0,y.zw)(e.name),1)],8,L)])))),128)):((0,n.wg)(!0),(0,n.iD)(n.HY,{key:1},(0,n.Ko)(r.enteredRooms,(e=>((0,n.wg)(),(0,n.iD)("section",{key:e,style:{padding:"5px"}},[(0,n._)("div",{class:"room-item",onClick:t=>a.openChatRoom(e)},[(0,n._)("p",null,(0,y.zw)(e.name),1)],8,$)])))),128))])],1024))])]),(0,n._)("section",E,[(0,n._)("div",j,[(0,n._)("section",D,[(0,n._)("p",null,[(0,n.Uk)("접속 중인 방 이름 : "),(0,n._)("span",null,(0,y.zw)(a.enteredRoomName),1)])]),r.isLoading?((0,n.wg)(),(0,n.j4)(c,{key:1})):((0,n.wg)(),(0,n.iD)("section",M,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(r.messages,(e=>((0,n.wg)(),(0,n.iD)("div",{key:e,style:{width:"100%","min-height":"60px",margin:"10px",padding:"10px"}},[(0,n._)("div",N,(0,y.zw)(e),1)])))),128))])),(0,n._)("section",A,[(0,n.wy)((0,n._)("input",{class:"chat-input",type:"text",onKeyup:t[2]||(t[2]=(0,s.D2)(((...e)=>a.send&&a.send(...e)),["enter"])),"onUpdate:modelValue":t[3]||(t[3]=e=>r.textMessage=e)},null,544),[[s.nr,r.textMessage]]),(0,n._)("button",{class:"chat-input-send-btn",type:"button",onClick:t[4]||(t[4]=(...e)=>a.send&&a.send(...e))},"보내기")])])])])])}var K=o(9840),z={name:"App",data(){return{activeRoomTab:"ALL",rooms:[],enteredRooms:[],isLoading:!1,currentRoom:null,websocketClient:null,textMessage:"",messages:[]}},computed:{activatedRoomTab(){return"ALL"===this.activeRoomTab?"active-all-room":"all-room"},activatedEnteredTab(){return"MINE"===this.activeRoomTab?"active-my-room":"my-room"},isAllTab(){return"ALL"===this.activeRoomTab},enteredRoomName(){return this.currentRoom?this.currentRoom.name:""}},created(){this.loadRooms()},mounted(){this.rooms=this.$store.getters["user/rooms"],this.enteredRooms=this.$store.getters["user/enteredRooms"]},beforeUnmount(){this.disconnect()},methods:{async loadRooms(){try{await this.$store.dispatch("user/loadRooms"),await this.$store.dispatch("user/loadEnteredRooms"),this.rooms=this.$store.getters["user/rooms"],this.enteredRooms=this.$store.getters["user/enteredRooms"]}catch(e){console.log(e)}},activeTab(e){this.activeRoomTab=e},async openChatRoom(e){this.isLoading=!0,await this.disconnect(),this.currentRoom=e,this.clear(),await this.connect()},disconnect(){this.websocketClient&&(this.websocketClient.publish({destination:`/pub/room/${this.currentRoom.id}/leave`,body:JSON.stringify({message:`${this.textMessage}`,writer:"user1"})}),this.websocketClient.deactivate())},connect(){const e="ws://localhost:8081/ws/init";this.websocketClient=new K.K({brokerURL:e,onConnect:()=>{this.websocketClient.subscribe(`/sub/room/${this.currentRoom.id}`,(e=>{this.messages.push(e.body)})),this.websocketClient.publish({destination:`/pub/room/${this.currentRoom.id}/entered`,body:JSON.stringify({message:`${this.textMessage}`,writer:"user1"})}),this.isLoading=!1},onWebSocketError:()=>{this.isLoading=!1}}),this.websocketClient.activate()},send(){this.websocketClient&&this.websocketClient.publish({destination:`/pub/room/${this.currentRoom.id}`,body:JSON.stringify({message:`${this.textMessage}`,writer:"user1"})})},clear(){this.textMessage="",this.messages=[]}}};const S=(0,l.Z)(z,[["render",I]]);var U=S;const H=(0,s.ri)(U);H.use(R),H.component("base-spinner",h),H.mount("#app")}},t={};function o(s){var n=t[s];if(void 0!==n)return n.exports;var i=t[s]={exports:{}};return e[s].call(i.exports,i,i.exports,o),i.exports}o.m=e,function(){var e=[];o.O=function(t,s,n,i){if(!s){var r=1/0;for(l=0;l<e.length;l++){s=e[l][0],n=e[l][1],i=e[l][2];for(var a=!0,c=0;c<s.length;c++)(!1&i||r>=i)&&Object.keys(o.O).every((function(e){return o.O[e](s[c])}))?s.splice(c--,1):(a=!1,i<r&&(r=i));if(a){e.splice(l--,1);var d=n();void 0!==d&&(t=d)}}return t}i=i||0;for(var l=e.length;l>0&&e[l-1][2]>i;l--)e[l]=e[l-1];e[l]=[s,n,i]}}(),function(){o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,{a:t}),t}}(),function(){o.d=function(e,t){for(var s in t)o.o(t,s)&&!o.o(e,s)&&Object.defineProperty(e,s,{enumerable:!0,get:t[s]})}}(),function(){o.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){var e={143:0};o.O.j=function(t){return 0===e[t]};var t=function(t,s){var n,i,r=s[0],a=s[1],c=s[2],d=0;if(r.some((function(t){return 0!==e[t]}))){for(n in a)o.o(a,n)&&(o.m[n]=a[n]);if(c)var l=c(o)}for(t&&t(s);d<r.length;d++)i=r[d],o.o(e,i)&&e[i]&&e[i][0](),e[i]=0;return o.O(l)},s=self["webpackChunkfront_end"]=self["webpackChunkfront_end"]||[];s.forEach(t.bind(null,0)),s.push=t.bind(null,s.push.bind(s))}();var s=o.O(void 0,[998],(function(){return o(2913)}));s=o.O(s)})();
//# sourceMappingURL=app.2511fa26.js.map