const fetchApi = async () => {
  console.log("You have clicked the button!");
  try{
    const response = await fetch("http://localhost:8080/custom");
    const text = await response.text();
    console.log("Response: ", text)
  }catch(e){
    console.error(e);
  }
}

function App() {
  return (
    <div className="App">
      <button id="clickMe" onClick={fetchApi}>8080</button>
    </div>
  );
}
export default App;