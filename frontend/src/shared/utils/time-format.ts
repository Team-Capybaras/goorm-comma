export function formatDateTimePad(dateString: string): string {
  const date = new Date(dateString)

  const year = date.getFullYear()
  const month = date.getMonth() + 1 // 0-based
  const day = date.getDate()

  const hours = date.getHours()
  const minutes = date.getMinutes().toString().padStart(2, '0')

  return `${year}.${month}.${day} ${hours}:${minutes}`
}