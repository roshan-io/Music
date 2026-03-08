import { initializeApp } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-app.js";
import { getFirestore, doc, setDoc } from "https://www.gstatic.com/firebasejs/10.7.0/firebase-firestore.js";

const firebaseConfig = {
  apiKey: "TEST",
  authDomain: "TEST",
  projectId: "TEST",
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

window.writeData = async function () {
  await setDoc(doc(db, "test", "hello"), {
    message: "Hello from APK"
  });
  alert("Data written");
};
