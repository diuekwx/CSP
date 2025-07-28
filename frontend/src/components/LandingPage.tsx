import LoginButton from "./LoginButton";

export default function LandingPage() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-pink-100 to-indigo-200 text-gray-800">
      <header className="text-center mb-10">
        <h1 className="text-5xl font-extrabold mb-4 drop-shadow">
          CSPHub
        </h1>
        <p className="text-xl max-w-xl mx-auto">
          Platform for productivity tracking and managing files for artists!!!!
        </p>
      </header>

      <LoginButton />

      <section className="mt-16 grid grid-cols-1 sm:grid-cols-2 gap-8 max-w-4xl w-full px-6">
        <div className="bg-white/60 rounded-2xl p-6 shadow-md backdrop-blur">
          <h2 className="text-2xl font-semibold mb-2">🗂 Version Control</h2>
          <p>Track changes to your artwork and see your progress !!!</p>
        </div>
{/* 
        <div className="bg-white/60 rounded-2xl p-6 shadow-md backdrop-blur">
          <h2 className="text-2xl font-semibold mb-2">🌐 Collaborate</h2>
          <p>Invite others to contribute, give feedback, or build on top of your work. (In Progress)</p>
        </div>

        <div className="bg-white/60 rounded-2xl p-6 shadow-md backdrop-blur">
          <h2 className="text-2xl font-semibold mb-2">📁 Gallery Mode</h2>
          <p>Showcase your finished projects or works in progress in a beautiful gallery. (In Progress)</p>
        </div> */}

        <div className="bg-white/60 rounded-2xl p-6 shadow-md backdrop-blur">
          <h2 className="text-2xl font-semibold mb-2">🔒 Private or Public</h2>
          <p>Decide who sees your work O_O</p>
        </div>
      </section>

    </div>
  );
}
