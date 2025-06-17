<script>
import { Terminal } from 'xterm';
import { FitAddon } from 'xterm-addon-fit';
import { AttachAddon } from 'xterm-addon-attach';
import 'xterm/css/xterm.css';

export default {
  name: 'TerminalComponent',
  data() {
    return {
      term: null,
      socket: null,
      fitAddon: new FitAddon()
    };
  },
  mounted() {
    this.initTerminal();
    this.connectWebSocket();
    this.handleResize();
  },
  beforeDestroy() {
    this.socket?.close();
    this.term?.dispose();
    window.removeEventListener('resize', this.debounceResize);
  },
  methods: {
    initTerminal() {
      this.term = new Terminal({
        cursorBlink: true,
        fontFamily: '"Cascadia Code", Consolas, monospace',
        fontSize: 14,
        rows: 30,
        theme: {
          background: '#1e1e1e',
          foreground: '#d4d4d4'
        }
      });

      this.term.loadAddon(this.fitAddon);
      this.term.open(document.getElementById('terminal-container'));
      this.fitAddon.fit();
    },

    connectWebSocket() {
      this.socket = new WebSocket("ws://localhost:8080/websocket/shell")
      this.socket.onopen = () => {
        const attachAddon = new AttachAddon(this.socket);
        this.term.loadAddon(attachAddon);
      };

      this.socket.onclose = () => {
        this.term.write('\r\n\x1b[31m连接已关闭\x1b[m');
      };

      this.socket.onerror = (error) => {
        this.term.write(`\r\n\x1b[31mWebSocket错误: ${error.message}\x1b[m`);
      };
    },

    handleResize() {
      this.debounceResize = this.debounce(() => {
        this.fitAddon.fit();
      }, 300);

      window.addEventListener('resize', this.debounceResize);
    },

    debounce(func, wait) {
      let timeout;
      return () => {
        clearTimeout(timeout);
        timeout = setTimeout(func, wait);
      };
    }
  }
};
</script>

<template>
  <div id="terminal-container"/>
</template>

<style scoped>
#terminal-container {
  float: right;
  width: 95%;
  height: 100%;
  background: #1e1e1e;
}
</style>