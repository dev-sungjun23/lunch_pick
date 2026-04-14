export default function MembersList({ members }) {
  if (!members.length) return null;
  return (
    <ul>
      {members.map((m) => (
        <li key={m.id}>
          {m.name} (id: {m.id})
        </li>
      ))}
    </ul>
  );
}
