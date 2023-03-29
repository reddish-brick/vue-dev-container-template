import { defineStore } from "pinia";

export const useTableStore = defineStore({
  id: "table",
  state: () => ({
    tableData: [
      {
        id: 1,
        name: "John Doe",
        age: 18,
        gender: "男",
        address: "武汉",
        email: "john@example.com",
      },
      {
        id: 2,
        name: "John Doe",
        age: 18,
        gender: "男",
        address: "武汉",
        email: "john@example.com",
      },
      {
        id: 3,
        name: "John Doe",
        age: 18,
        gender: "男",
        address: "武汉",
        email: "john@example.com",
      },
      {
        id: 4,
        name: "John Doe",
        age: 18,
        gender: "男",
        address: "武汉",
        email: "john@example.com",
      },
    ],
  }),
  actions: {
    replaceTableData(newTableData) {
      this.tableData.splice(0, this.tableData.length, ...newTableData);
    },
    addData(newOneTableData) {
      this.tableData.push(newOneTableData);
    },
    deleteById(id) {
      const index = this.tableData.findIndex((data) => data.id === id);
      if (index >= 0) {
        this.tableData.splice(index, 1);
      }
    },
    updateById(id, updates) {
      const index = this.tableData.findIndex((data) => data.id === id);
      if (index >= 0) {
        Object.assign(this.tableData[index], updates);
      }
    },
  },
});
