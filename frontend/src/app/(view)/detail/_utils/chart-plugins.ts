import {Plugin} from 'chart.js'

/* 차트와 범례 사이의 간격 Plugin */
export const legendMarginPlugin = (
  spacing: number = 46
): Plugin<'line'> => ({
  id: 'legendMargin',
  beforeInit(chart, options) {
    const legend = chart.legend
    if (!legend) return

    const originalFit = legend.fit

    legend.fit = function fit() {
      originalFit.bind(legend)()
      return (this.height += spacing)
    }
  },
})

/* 임의 Tooltip Plugin */
type TooltipBubbleOptions = {
  hour: number
  text: (hour: number) => string
}

export const tooltipBubblePlugin = ({
    hour,
    text,
  }: TooltipBubbleOptions): Plugin<'line'> => ({
  id: 'tooltipBubble',
  afterDraw(chart) {
    const { ctx, scales, chartArea  } = chart

    if (!chartArea) return

    const xScale = scales.x

    let x = xScale.getPixelForValue(hour)

    const topY = scales.y.top - 35
    const message = text(hour)

    const padding = 10
    ctx.font = '12px Pretendard'
    const textWidth = ctx.measureText(message).width

    const bubbleWidth = textWidth + padding * 2
    const bubbleHeight = 28

    const half = bubbleWidth / 2
    const minX = chartArea.left + half
    const maxX = chartArea.right - half

    if (x < minX) x = minX
    if (x > maxX) x = maxX

    ctx.save()

    ctx.fillStyle = '#3B82F6'
    ctx.beginPath()
    ctx.roundRect(
      x - half,
      topY,
      bubbleWidth,
      bubbleHeight,
      14
    )
    ctx.fill()

    ctx.fillStyle = '#FFFFFF'
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    ctx.fillText(message, x, topY + bubbleHeight / 2)

    ctx.restore()
  },
})

/* 임의 vertical line plugin */
type VerticalLineOptions = {
  hour: number
  color?: string
}

export const verticalLinePlugin = ({
    hour,
    color = '#0C4596',
  }: VerticalLineOptions): Plugin<'line'> => ({
  id: 'verticalLine',
  afterDraw(chart) {
    const { ctx, scales, chartArea } = chart
    if (!chartArea) return

    const xScale = scales.x
    const yScale = scales.y

    const x = xScale.getPixelForValue(hour)

    ctx.save()

    // 점선
    ctx.setLineDash([4, 4])
    ctx.strokeStyle = color ? color : '#0C4596'
    ctx.lineWidth = 1
    ctx.beginPath()
    ctx.moveTo(x, yScale.top)
    ctx.lineTo(x, yScale.bottom)
    ctx.stroke()

    // 삼각형
    const triangleSize = 6
    const triangleY = chartArea.top

    ctx.setLineDash([])
    ctx.fillStyle = color ? color : '#0C4596'
    ctx.beginPath()
    ctx.moveTo(x - triangleSize, triangleY)
    ctx.lineTo(x + triangleSize, triangleY)
    ctx.lineTo(x, triangleY + triangleSize)
    ctx.closePath()
    ctx.fill()

    ctx.restore()
  },
})